var restify = require('restify');
var consign = require('consign');


var expressValidator = require('express-validator');
var mongoose = require('mongoose');
var passport = require('passport');
var flash    = require('connect-flash');

var morgan       = require('morgan');
var cookieParser = require('cookie-parser');
var session      = require('express-session');
//var logger = require('../infra/logger.js');
var cors = require('cors');


module.exports = function() {

  var app = restify.createServer({
    name: 'Coffee TVShow Server'
  });

  require('./passport')(passport); // pass passport for configuration

  app.use(morgan("dev", {
    stream: {
      write: function(message){
        logger.debug(message)
      }
    }
  }));

  app.use(cors({
    origin: "http://localhost:8080",
    methods: ["GET", "POST", "PUT", "DELETE"],
    allowedHeaders: "Content-type"
  }));

  app.use(cookieParser());
  app.use(restify.queryParser());
  app.use(restify.jsonp());

  app.use(restify.bodyParser());
  //app.set('view engine', 'ejs'); // set up ejs for templating

  // required for passport
  app.use(session({ secret: 'ilovescotchscotchyscotchscotch' })); // session secret
  app.use(passport.initialize());
  app.use(passport.session()); // persistent login sessions
  app.use(flash()); // use connect-flash for flash messages stored in session

  require('../routes/routes.js')(app, passport); // load our routes and pass in our app and fully configured passport

  app.use(expressValidator()); //Obrigatoriamente logo apos o bodyParser

  consign({verbose: false})
  .include('config')
  .then('infra')
  .then('models')
  .then('routes')
  .then('views')
  .into(app);

  return app;
}
