// package SevenSeg

// import chisel3._
// import chiseltest._
// import org.scalatest.flatspec.AnyFlatSpec
// import SevenSegDemo.SevenSegShift

// class SevenSegShiftTest extends AnyFlatSpec with ChiselScalatestTester {
// 	"SevenSegShiftTest test" should "pass" in {
// 		test(new SevenSegShift(4)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 
// 			for (i <- 0 until 500) {
// 				println(dut.io.anode.peek())
// 				dut.clock.step(1)
// 			}
		
// 		}
// 	}
// }