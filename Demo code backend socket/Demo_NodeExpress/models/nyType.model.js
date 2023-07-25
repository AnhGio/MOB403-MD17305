const db = require('../db_connection');

const nyTypeSchema = new db.mongoose.Schema ({
    name: {type: String, require: true},
    description: {type: String, require: false},
}, {collection: 'tb_nyType'}
);

let nyTypeModel = db.mongoose.model('nyTypeModel', nyTypeSchema);

module.exports = nyTypeModel