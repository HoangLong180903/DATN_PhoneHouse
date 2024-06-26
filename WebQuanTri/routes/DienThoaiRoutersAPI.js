var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/DienThoai')
require('../models/HangSanXuat')

const DienThoai = mongoose.model("dienthoai")
const HangSX = mongoose.model("hangSanXuat")


/* GET DienThoai listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});
router.post('/addDienThoai', function (req, res, next) {
  const dienthoai = new DienThoai({
    tenDienThoai: req.body.tenDienThoai,
    giaTien: req.body.giaTien,
    soLuong: req.body.soLuong,
    anh: req.body.anh,
    maMau: req.body.maMau,
    maRam: req.body.maRam,
    maDungLuong: req.body.maDungLuong,
    maHangSX: req.body.maHangSX,
    maUuDai: req.body.maUuDai,
    maChiTiet: req.body.maChiTiet,
  })
  dienthoai.save()
      .then(data => {
        console.log(data)
        res.send(data)
      }).catch(err => {
    console.log
  })
});

router.get('/getDienThoai', async (req, res) => {
  try {
    const dienThoai = await DienThoai.find()
        .populate('maUuDai')
        .populate('maMau')
        .populate('maRam')
        .populate('maDungLuong')
        .populate('maHangSX')
        .populate('maChiTiet')
    res.json(dienThoai);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
})


router.delete('/deleteDienThoai/:id', async (req, res) => {
  try {
    const data = await DienThoai.findByIdAndDelete(req.params.id)
    if (!data) {
      return res.status(404).json({message: "delete failed"})
    } else {
      return res.status(200).json({message: "delete successful"})
    }
  } catch (err) {
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateDienThoai/:id", async (req, res) => {
  try {
    const data = await DienThoai.findByIdAndUpdate(req.params.id, req.body, {new: true})
    if (!data) {
      return res.status(404).json({message: "update failed"})

    } else {
      return res.status(200).json({message: "update successful"})

    }
  } catch (err) {
    return res.status(500).json({message: err.message})
  }
})

router.get("/getDienthoaiTheoHangSanXuat/:id", async (req, res) => {
  try {
    const idHangSanXuat = req.params.id;
    const dienThoai = await DienThoai.find({maHangSX: idHangSanXuat})
        .populate('maHangSX', '_id')
        .populate('maHangSX')
    res.json(dienThoai)
  } catch (error) {
    return res.status(500).json({message: error.message})
  }
})

router.get("/getDienthoaiTheoCuaHang/:id", async (req, res) => {
  try {
    const idCuaHang = req.params.id;
    const hangSX = await HangSX.find({maCuaHang: idCuaHang})
        .populate('maCuaHang', '_id')
        .populate('maCuaHang')
    const dienThoais = [];
    for (const hang of hangSX) {
      const dienThoai = await DienThoai.find({ maHangSX: hang._id })
          .populate('maHangSX', '_id')
          .populate('maHangSX');
      if (dienThoai){
        dienThoais.push(...dienThoai);
      }
    }
    res.json(dienThoais)
  } catch (error) {
    return res.status(500).json({message: error.message})
  }
})

router.put("/updateUuDaiDienThoai/:id", async (req, res) => {
  try {
    const data = await DienThoai.findByIdAndUpdate(req.params.id, { maUuDai: req.body.maUuDai }, {new: true})
    if (!data) {
      return res.status(404).json({message: "update failed", data})
    } else {
      return res.status(200).json({message: "update successful", data})
    }
  } catch (err) {
    return res.status(500).json({message: err.message})
  }
})
module.exports = router;
