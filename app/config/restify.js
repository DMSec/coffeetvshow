var restify = require('restify');
var consign = require('consign');


var expressValidator = require('express-validator');
var morgan = require('morgan');
var logger = require('../infra/logger.js');
var cors = require('cors');


module.exports = function() {

  var app = restify.createServer({
    name: 'Coffee TVShow Server'
  });


  app.use(morgan("common", {
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


  app.use(restify.queryParser());
  app.use(restify.jsonp());

  app.use(restify.bodyParser());

  app.use(expressValidator()); //Obrigatoriamente logo apos o bodyParser

  consign({cwd: 'app'})
  .into(app);

  return app;
}
