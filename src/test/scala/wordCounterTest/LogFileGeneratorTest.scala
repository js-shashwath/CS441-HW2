package wordCounterTest

import Generation.RandomStringGenerator
import com.mifmif.common.regex.Generex
import com.typesafe.config.ConfigFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.shouldBe
import Generation.RSGStateMachine.unit
import HelperUtils.Parameters
import org.scalatest.PrivateMethodTester
import language.deprecated.symbolLiterals

//import Generation.RSGStateMachine.unit

class LogFileGeneratorTest extends AnyFlatSpec with Matchers with PrivateMethodTester{

  val minLength = 10
  val maxLength = 10
  val INITSTRING = "Starting the string generation"
  val randomSeed = 1
  val config = ConfigFactory.load()

  // To check whether the timeStamp startTime is same as the one given in config file
  it should "check the startTime in config" in {
    config.getString("startTime") shouldBe "02:54:33.439"
  }

  // To check whether the timeStamp endTime is same as the one given in config file
  it should "check the endTime in config" in {
    config.getString("endTime") shouldBe "02:55:35.459"
  }

  // To check whether the seperator used is a comma
  it should "check the dot match" in {
    config.getString("dot") shouldBe "."
  }

  // find the pattern in the string
  it should "find the pattern in the string" in {
    val pattern = "\\*"
    val gen: Generex = new Generex(pattern)
    val genString = gen.random()
    genString should include regex pattern.r
  }

  // test to check if the generated string matches the patter specified, if not this test returns a string of length 0
  it should "return the same input string if the constructed random string is zero length" in {
    val someString = "someString"
    val rsg = RandomStringGenerator((1,2), 1)
    val callConstruct = PrivateMethod[String]('constructString)
    val result:String = rsg invokePrivate callConstruct(someString, 0)
    result should fullyMatch regex someString
  }


}