# How to use input:

Graph a function by adding "GRAPH:" and then the function id

Function 0 is x^3-x
Function 1 is x^0.5+x
Function 2 is (x-2)f0(x) or (x-2)(x^3-x)
Function 3 is (x/4)f2(x) or (x/4)((x-2)(x^3-x))
Function 4 is 4.5cos(x)
Function 5 is ((f2(x)/x)^2)^0.1 or (((x-2)(x^3-x)/x)^2)^0.1
Function 6 is 1.5sin(x)/f4(x) or (1.5sin(x))/(4.5cos(x))

To change attributes about the graph add "FLAG:" followed by the name of what you want to change.
Flags stay in effect until they are cleared with "FLAG:CLEAR" or changed

To change the color of the graphs below, add "FLAG:COLOR_ID:" and then the id of the color
   0: red
   1: green
   2: blue
   3: yellow
   4: cyan
   5: pink
   6: white

If you want to graph the derivative of a function, add "FLAGS:GRAPH_DERIVATIVE:n" where is n is the degree (n ≥ 0).
So if you added, "FLAG:GRAPH_DERIVATIVE:3", it would graph the third degree derivative of all following functions, until it is called again or cleared.
Be warned that the higher the degree, the laggier the application becomes as well as the return number being less accurate.

To take a derivative, add "DERIVE:x,y,z", where x is the function ID, y is the location to be derived at, and z is the degree.#This does not account for locations where the derivative doesn't exist, however.

To take an integral, add "INTEGRATE:x,y,z", where x is the function ID, y is the starting value, and z is the ending value.
Also note that integrals don't include exceptions when y aproaches 0, so taking an integral of some functions might give you an inaccurate value instead of DNE.
