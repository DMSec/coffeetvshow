var OAuth2Strategy = require('passport-oauth2');


module.exports = function(configAuth,User){
  var TvShowTimeStrategy = new OAuth2Strategy({
        authorizationURL: 'https://www.tvshowtime.com/oauth/authorize',
        tokenURL: 'https://api.tvshowtime.com/v1/oauth/access_token',
        clientID: configAuth.tvshowAuth.clientID,
        clientSecret: configAuth.tvshowAuth.clientSecret,
        callbackURL: configAuth.tvshowAuth.callbackURL,
        passReqToCallback : true // allows us to pass in the req from our route (lets us check if a user is logged in or not)
      },
        function(req, token, refreshToken, profile, done) {

          ///Mudar isso aqui de lugar
          var api = require('../tvshowtime/tvshowtime-api')
          var tv = new api(token)
          var usertv = tv.getUser();
          console.log("tvshow meu "+JSON.parse(usertv));
            // asynchronous
            process.nextTick(function() {

                // check if the user is already logged in
                if (!req.user) {

                    User.findOne({ 'tvshowtime.id' : profile.id }, function(err, user) {
                        if (err)
                            return done(err);

                        if (user) {
                            // if there is a user id already but no token (user was linked at one point and then removed)
                            if (!user.tvshowtime.token) {
                                user.tvshowtime.token = token;
                                user.tvshowtime.name  = profile.displayName;
                                //user.tvshowtime.email = (profile.emails[0].value || '').toLowerCase(); // pull the first email

                                user.save(function(err) {
                                    if (err)
                                        return done(err);

                                    return done(null, user);
                                });
                            }

                            return done(null, user);
                        } else {
                            var newUser          = new User();

                            newUser.tvshowtime.id    = profile.id;
                            newUser.tvshowtime.token = token;
                            newUser.tvshowtime.name  = profile.displayName;
                            newUser.tvshowtime.email = (profile.emails[0].value || '').toLowerCase(); // pull the first email

                            ///Mudar isso aqui de lugar
                            var api = require('tvshowtime-api')
                            var tv = new api(token)
                            var usertv = tv.getUser();
                            console.log(usertv.result);

                            newUser.save(function(err) {
                                if (err)
                                    return done(err);

                                return done(null, newUser);
                            });
                        }
                    });

                } else {
                    // user already exists and is logged in, we have to link accounts
                    var user               = req.user; // pull the user out of the session

                    user.tvshowtime.id    = profile.id;
                    user.tvshowtime.token = token;
                    user.tvshowtime.name  = profile.displayName;
                    //user.tvshowtime.email = (profile.emails[0].value || '').toLowerCase(); // pull the first email

                    ///Mudar isso aqui de lugar
                    var api = require('../tvshowtime/tvshowtime-api')
                    var tv = new api(token)
                    var usertv = tv.getUser();
                    console.log(usertv.result);

                    user.save(function(err) {
                        if (err)
                            return done(err);

                        return done(null, user);
                    });

                }

            });

        }
  );


  return TvShowTimeStrategy;

}
