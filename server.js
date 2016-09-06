var app = require('./app/config/restify')();

var host = process.env.HOST || '127.0.0.1';
var port = process.env.PORT || '8080';


app.listen(port,host, function() {
  console.log('%s listening at %s', app.name, app.url);
});
