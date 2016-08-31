var restify = require('restify');
var host = process.env.HOST || '127.0.0.1';
var port = process.env.PORT || '8080';


var app = restify.createServer({
  name: 'Coffee TVShow Server'
});



app.use(restify.queryParser());
app.use(restify.bodyParser());

app.use(function logger(req,res,next) {
  console.log(new Date(),req.method,req.url);
  next();
});



app.on('uncaughtException',function(request, response, route, error){
  console.log(new Date(),request.method,request.url);
  console.error(error.stack);
  response.send(error);
});

app.listen(port,host, function() {
  console.log('%s listening at %s', app.name, app.url);
});
