var Client = require('node-rest-client').Client;

var baseURL = require('../config/api-clients/settings.json').api;

module.exports = function(app, passport) {

  // TVShowTime ---------------------------------

      // send to tvshowtime to do the authentication
      app.get('/auth/tvshowtime', passport.authenticate('tvshowtime', { scope : ['profile', 'email'] }),function(req,res){
        console.log('passei aqui /auth/tvshowtime');
      });

      // the callback after google has authenticated the user
      app.get('/auth/tvshowtime/callback?:code',  passport.authenticate('tvshowtime',{ successRedirect : '/profile',
              failureRedirect : '/'})
        );

          // tvshowtime ---------------------------------
        // send to tvshowtime to do the authentication
        app.get('/connect/tvshowtime', passport.authorize('tvshowtime', { scope :['profile', 'email'] }),function(res,req){
                console.log('passei aqui - /connect/tvshowtime');
        });

              // the callback after tvshowtime has authorized the user
        app.get('/connect/tvshowtime/callback/:code',passport.authorize('tvshowtime', {
                                successRedirect : '/profile',
                      failureRedirect : '/'
        }));

        //Rota para lista episodios a serem assistidos...
        app.get('/tvshowtime/watch',isLoggedIn,function(req,res,next){
            var user = req.user;
            if(user.tvshowtime.token != undefined){
                  var client = new Client();
                  var args = {
                      path: { "access_token": user.tvshowtime.token }, // path substitution var
                      headers: { "test-header": "client-api" } // request headers
          };
              // registering remote methods
              client.registerMethod("jsonMethod", baseURL+"/to_watch?&req=user&access_token=${access_token}", "GET");

          client.methods.jsonMethod(args, function (data, res) {
            console.log( user.tvshowtime.token );
            console.log(data.episodes[0].id);
            console.log("Teste");
            return next();
          });


            }
        });




        // google ---------------------------------
        app.get('/unlink/tvshowtime', isLoggedIn, function(req, res) {
            var user          = req.user;
            user.tvshowtime.token = undefined;
            user.save(function(err) {
                res.redirect('/profile');
            });
        });

};


// route middleware to ensure user is logged in
function isLoggedIn(req, res, next) {
    if (req.isAuthenticated())
        return next();

    res.redirect('/');
}
