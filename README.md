# Chisel Seven Segment Display
The topLevelDisplay module allows for the display of numbers or segments to the 7 segment display on our Basys3. Below is block diagram detailing the structure of the module and its submodules

 <img src=".\image.png" alt="Alt text" style="width: 100%; height: 100%;">

Here is the usage:
- to display a number held in the register currentValue:
 ```bash
val display = Module(new topLevelDisplay(true))
display.io.input := currentValue
```

- to display a specific segments:
 ```bash
val display = Module(new topLevelDisplay(false)) 
val segs = Wire(Vec(4, UInt(7.W)))   

segs(0) := "b1111000".U 
segs(1) := "b1001000".U
segs(2) := "b1001000".U
segs(3) := "b1001110".U

display.io.input := segs
```

- You still have to connect up the signals to your top level design:
```bash
io.board_segments := display.io.segments
io.board_anode := display.io.anode
```

Your .xdc file should look like this:
```bash
set_property PACKAGE_PIN W7 [get_ports {io_board_segments[6]}]  
set_property PACKAGE_PIN W6 [get_ports {io_board_segments[5]}]  
set_property PACKAGE_PIN U8 [get_ports {io_board_segments[4]}]  
set_property PACKAGE_PIN V8 [get_ports {io_board_segments[3]}] 
set_property PACKAGE_PIN U5 [get_ports {io_board_segments[2]}]   
set_property PACKAGE_PIN V5 [get_ports {io_board_segments[1]}] 
set_property PACKAGE_PIN U7 [get_ports {io_board_segments[0]}] 
 
set_property PACKAGE_PIN U2 [get_ports {io_board_anode[0]}]
set_property PACKAGE_PIN U4 [get_ports {io_board_anode[1]}]
set_property PACKAGE_PIN V4 [get_ports {io_board_anode[2]}]
set_property PACKAGE_PIN W4 [get_ports {io_board_anode[3]}]
```


**Getting the Repo:**
```bash
$ git clone https://github.com/ebiernacki/chisel-chisel-7seg.git 
```

## External Setup Guides and Running the Projects

- [Java, Sbt and Chisel Guide](https://docs.google.com/document/d/13pX-4cFuGuj_i7VRhmksyf7YL6-qXiF8-O9J9m_yVfI/edit?usp=sharing)
- [GTKWave Guide](https://docs.google.com/document/d/1-muYy8XSGP4EbMIbLuwTEscIj1UC-u8HU5glcBpIFUo/edit?usp=sharing)
- [Vivado Guide](https://docs.google.com/document/d/1O-y1rnS1V_Bjyc2GwYd9C6Gq1IsqVcxacy2lTD6tHME/edit?usp=sharing)





