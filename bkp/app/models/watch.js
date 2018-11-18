var mongoose = require('mongoose');


var episodeSchema = mongoose.Schema({

      episode : {
        ep-id : String,
        ep-name : String,
        ep-number : String,
        ep-season_number : String,
        ep-show_id : String,
        ep-show_name : String,
        ep-seen : String,
        ep-images_screen_1: String
      }
});

// create the model for users and expose it to our app
module.exports = mongoose.model('Episode', episodeSchema);
