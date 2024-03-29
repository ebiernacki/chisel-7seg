package displayDriver

import chisel3._
import chisel3.util._
import _root_.circt
import chisel3.stage.{ChiselGeneratorAnnotation}
import Helper.AnyCounter
import Helper.binToBCD
import Helper.segLookupBinToBCD

/*
*
* Top level display module to set a number using BCD or set any LED segment on any character position
* Allows for dynamically setting any segment on any position 
* Full Modularity is the goal
*/

class topLevelDisplay(numberInput : Boolean) extends Module{
    val io = IO(new Bundle{
        val input  = if(numberInput) Input(UInt(14.W)) else Input(Vec(4, UInt(7.W))) 

        //values to pass to board_ pins output
        val segments  = Output(UInt(7.W)) 
        val anode     = Output(UInt(4.W))
    })

    val chosenSegments = Wire(Vec(4, UInt(7.W)))

    //NOTE: SCALA if/else does not generate hardware, it simply decides at runtime what to generate
    //numberInput is known at compiler
    if(numberInput){

        //now that we have the number we need to display it
        //but our display module allows us to address each character individually, not as a whole
        //for example, say our current value is 1024. How do we tell each character to take 1, 0, 2, and 4 individually from our register that holds the unsigned integer???
        
        //Introducing binary to BCD(Binary coded decimal) using the double dabble algo.
        //binary to BCD allows us to encode each digit of an binary int value into a 4bit binary representation(BCD) using the double dabble algo 
        //Video explaining double dabble: https://www.youtube.com/watch?v=eXIfZ1yKFlA 
        //wiki link: https://en.wikipedia.org/wiki/Double_dabble

        val binToBCDConverter = Module(new binToBCD)
        binToBCDConverter.io.bin := io.input

        val numberSegVec = Module(new segLookupBinToBCD)
        numberSegVec.io.in := binToBCDConverter.io.bcd

        //using our new segLookup, we have looked up each 4bit BCD value for each digit in our currentValue number
        //remember the output of our new segLoopup is the segments of each of these digits
        //all we need now is to connect them up to our display module
        
        chosenSegments := numberSegVec.io.SegVec
    }
    else{
        chosenSegments := io.input
    }

    val characterSelect = Module(new characterSelectFSM)
    characterSelect.io.char0 := chosenSegments(0)
    characterSelect.io.char1 := chosenSegments(1)
    characterSelect.io.char2 := chosenSegments(2)
    characterSelect.io.char3 := chosenSegments(3)


    //1 is on for a segmenet 0 is off until this point
    //See board spec for active low 
    io.segments  :=  ~characterSelect.io.segmenents
    io.anode     :=  ~characterSelect.io.anodes

}