const db = require('../db_connection');

const nySchema = new db.mongoose.Schema ({
        name: {type: String, require: true},
        phone: {type: String, require: true },
        description: {type: String, require: false},
        type: {type: db.mongoose.Schema.Types.ObjectId, ref: 'nyTypeModel'}
}, {collection: 'tb_ny'}
);

let nyModel = db.mongoose.model('nyModel', nySchema);

module.exports = nyModel

