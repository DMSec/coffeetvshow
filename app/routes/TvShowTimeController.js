var Client = require('node-rest-client').Client;

var baseURL = require('../api-clients/settings.json').api;


module.exports = function(app,passport){

      var client = new Client();
      var args = {
            path: { "access_token": token }, // path substitution var
            headers: { "test-header": "client-api" } // request headers
      };

      // registering remote methods
      client.registerMethod("jsonMethod", baseURL+"/"+param+"?&req=user&access_token=${access_token}", "GET");

      client.methods.jsonMethod(args, function (data, response) {
        console.log(data);
      }



}

// route middleware to ensure user is logged in
function isLoggedIn(req, res, next) {
    if (req.isAuthenticated())
        return next();

    res.redirect('/');
}
