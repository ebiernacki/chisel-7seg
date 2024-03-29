package usingDisplay

import chisel3._
import chisel3.util._
import _root_.circt
import chisel3.stage.{ChiselGeneratorAnnotation}
import displayDriver.topLevelDisplay


object diceSimulatorMain extends App{
    class diceSimulator extends Module {
        val io = IO(new Bundle {
            val board_btn = Input(Bool())
            val board_segments = Output(UInt(7.W))
            val board_anode = Output(UInt(4.W)) //characters
            
        })

        //generate a random number(1-6) on a button press
        val counter = RegInit(1.U(3.W))
        val randomVal = RegInit(0.U(3.W))
        
        when(counter === 6.U){
            counter := 1.U
        }.otherwise{
            counter := counter + 1.U
        }

        //on button press capture counter value
        when(io.board_btn && !RegNext(io.board_btn)) {
            randomVal := counter
            counter := 0.U
        }
        
        //output the values to the board using display
        val display = Module(new topLevelDisplay(true))
        display.io.input := randomVal

        io.board_anode := display.io.anode 
        io.board_segments := display.io.segments

    }

    new circt.stage.ChiselStage().execute(args,Seq(circt.stage.CIRCTTargetAnnotation(circt.stage.CIRCTTarget.SystemVerilog), ChiselGeneratorAnnotation(() => new diceSimulator)))
}