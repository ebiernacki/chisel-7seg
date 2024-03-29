package Demo

import chisel3._
import chisel3.util._
import _root_.circt
import chisel3.stage.{ChiselGeneratorAnnotation}



//Connect the Switches to the anodes, and hard code the segments to give:
//   _
//  | |
// 
// or a top hat
//
class simpleDisplay extends Module {
    val io = IO(new Bundle {
        val sw       = Input(UInt(4.W))
        val segments = Output(UInt(7.W))
        val anode    = Output(UInt(4.W)) //characters
    })

    val segments = "b1100010".U 

    io.segments := ~segments
    io.anode := ~io.sw

}


object simpleDisplayMain extends App{
    class simpleDisplayTop extends Module {
      	val io = IO(new Bundle {
            val board_sw        = Input(UInt(4.W))
        	val board_segments = Output(UInt(7.W))
        	val board_anode   = Output(UInt(4.W))
    	})

      	val display = Module(new simpleDisplay)
        display.io.sw := io.board_sw
        io.board_segments := display.io.segments
        io.board_anode := display.io.anode
    }  

    new circt.stage.ChiselStage().execute(args,Seq(circt.stage.CIRCTTargetAnnotation(circt.stage.CIRCTTarget.SystemVerilog), ChiselGeneratorAnnotation(() => new simpleDisplayTop)))
}

