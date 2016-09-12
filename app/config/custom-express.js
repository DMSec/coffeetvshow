// set up ======================================================================
// get all the tools we need
var express  = require('express');
var consign = require('consign');
var mongoose = require('mongoose');
var passport = require('passport');
var flash    = require('connect-flash');
var expressValidator = require('express-validator');
var morgan       = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser   = require('body-parser');
var session      = require('express-session');

var configDB = require('./database.js');
var cors = require('cors');


module.exports = function() {

    var app      = express();
    // configuration ===============================================================
    mongoose.connect(configDB.url); // connect to our database

    require('./passport')(passport); // pass passport for configuration

    app.use(cors({
      origin: "http://localhost:8080",
      methods: ["GET", "POST", "PUT", "DELETE"],
      allowedHeaders: "Content-type"
    }));



    // set up our express application
    app.use(morgan('dev')); // log every request to the console
    app.use(cookieParser()); // read cookies (needed for auth)
    app.use(bodyParser.json()); // get information from html forms
    app.use(bodyParser.urlencoded({ extended: true }));

    app.set('view engine', 'ejs'); // set up ejs for templating

    // required for passport
    app.use(session({ secret: 'coffeetvshowtimethebestever',
                      resave: true,
                      saveUninitialized: true
                    })); // session secret
    app.use(passport.initialize());
    app.use(passport.session()); // persistent login sessions
    app.use(flash()); // use connect-flash for flash messages stored in session

    // routes ======================================================================
    require('../routes/routes')(app, passport); // load our routes and pass in our app and fully configured passport

    app.use(expressValidator()); //Obrigatoriamente logo apos o bodyParser

    consign({verbose: false})
    .include('config')
    .then('infra')
    .then('models')
    .then('routes')
    .into(app);

    return app;

}



// launch ======================================================================
//app.listen(port);
//console.log('The magic happens on port ' + port);
