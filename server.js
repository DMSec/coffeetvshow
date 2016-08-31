var app = require('./app/config/custom-express')();

app.listen(3000,function(){
  console.log("Server started!");
});
