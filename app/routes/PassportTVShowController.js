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


        app.get('/tvshowtime/watch',passport.authorize('tvshowtime',{
                          successRedirect : '/listWatch',
                          failureRedirect : '/'
        }));

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
