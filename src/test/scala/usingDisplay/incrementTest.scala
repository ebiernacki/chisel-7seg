package usingDisplay

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class incrementTest extends AnyFlatSpec with ChiselScalatestTester {
	"incrementTest test" should "pass" in {
		test(new incrementMain.increment(40)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 
			for (i <- 0 until 500) {
				dut.clock.step(1)
			}
		}
	}
}