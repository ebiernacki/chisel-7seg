package usingDisplay


import chisel3._
import chisel3.util._
import _root_.circt
import chisel3.stage.{ChiselGeneratorAnnotation}
import displayDriver.topLevelDisplay


object edgeSegmentsMain extends App{
    class edgeSegments extends Module {
        val io = IO(new Bundle {
            val board_segments = Output(UInt(7.W))
            val board_anode = Output(UInt(4.W))
        })

        val display = Module(new topLevelDisplay(false)) //numberinput parameter changed to false
        val segs = Wire(Vec(4, UInt(7.W)))   
       
        segs(0) := "b1111000".U //
        segs(1) := "b1001000".U
        segs(2) := "b1001000".U
        segs(3) := "b1001110".U

        display.io.input := segs

        io.board_segments := display.io.segments
        io.board_anode := display.io.anode
    }

    new circt.stage.ChiselStage().execute(args,Seq(circt.stage.CIRCTTargetAnnotation(circt.stage.CIRCTTarget.SystemVerilog), ChiselGeneratorAnnotation(() => new edgeSegments)))
}