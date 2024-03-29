// package SevenSeg

// import chisel3._
// import chiseltest._
// import org.scalatest.flatspec.AnyFlatSpec
// import SevenSegDemo.simpleDisplay

// class simpleDisplayTest extends AnyFlatSpec with ChiselScalatestTester {
// 	"simpleDisplayTest test" should "pass" in {
// 		test(new simpleDisplay(4)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 
// 			for (i <- 0 until 500) {
// 				println(dut.io.segments.peek())
// 				dut.clock.step(1)
// 			}
// 		}
// 	}
// }