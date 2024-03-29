package usingDisplay


import chisel3._
import chisel3.util._
import _root_.circt
import chisel3.stage.{ChiselGeneratorAnnotation}
import Helper.AnyCounter
import displayDriver.topLevelDisplay


object incrementMain extends App{
    class increment(speed : Int) extends Module {
        val io = IO(new Bundle {
            val board_segments = Output(UInt(7.W))
            val board_anode = Output(UInt(4.W))
        })

        //create incrementing value that ticks up 1 time every 1/4 sec
        val counter = Module(new AnyCounter(speed/4, 32))
        val currentValue = RegInit(0.U(14.W)) //max value we can count to is 9999, which is 14 bits

        //increment the value
        currentValue := Mux(counter.io.flag, currentValue + 1.U, currentValue)


        //can either select a number or specific segments 
        val display = Module(new topLevelDisplay(true))
        display.io.input := currentValue

        io.board_segments := display.io.segments
        io.board_anode := display.io.anode
    }

    new circt.stage.ChiselStage().execute(args,Seq(circt.stage.CIRCTTargetAnnotation(circt.stage.CIRCTTarget.SystemVerilog), ChiselGeneratorAnnotation(() => new increment(100000000))))
}