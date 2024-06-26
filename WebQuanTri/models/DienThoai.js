const mongoose = require('mongoose');

const DienThoaiSchema = mongoose.Schema({
    tenDienThoai: {type: String},
    giaTien: {type: Number},
    soLuong: {type: Number},
    anh: {type: String},
    maMau: {type: mongoose.Schema.Types.ObjectId, ref: 'mau'},
    maRam: {type: mongoose.Schema.Types.ObjectId, ref: 'ram'},
    maDungLuong: {type: mongoose.Schema.Types.ObjectId, ref: 'dungluong'},
    maHangSX: {type: mongoose.Schema.Types.ObjectId, ref: 'hangSanXuat'},
    maUuDai: {type: mongoose.Schema.Types.ObjectId, ref: 'uudai'},
    maChiTiet: {type: mongoose.Schema.Types.ObjectId, ref: 'chitietdienthoai'},
});
module.exports = mongoose.model('dienthoai', DienThoaiSchema);