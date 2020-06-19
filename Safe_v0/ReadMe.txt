Part 1 
LockError:
⑤	sending '*1234' (to test that the safe behaves correctly in response to a code longer than 3 digits)
LockSuccess:
③	sending '****123#'(to test that the safe behaves correctly in response to multiple '*')
UnlockSuccess:
③	locking '*123#', unlocking '****123#' (to test that the safe behaves correctly in response to multiple '*')
UnlockError(locking, sending '*123#'):
⑥	sending '1'(to test that the safe behaves correctly in response to wrong format)

Part 2 
The test may fail because of the status of the safe after each testing may affect the next testing, 
so we have to reset the safe each time before start a new testing by adding the following reset codes:
logPort.log("[Harness] --------");
logPort.log("[Harness] sending 'reset'");
resetPort.reset().send();
LockError: we need to add reset in initial code.
LockSuccess: we don’t need to add reset because the reset is already executed in the Entry code of State1 in each loop.
UnlockSuccess: we need to add reset in initial code.
UnlockError: we need to add reset before locking the safe in the initial code.

Part 3 
inp1=randomDigit();
inp2=randomDigit();
inp3=randomDigit();
logPort.log("[Harness](LockWithRandom) sending '*%d%d%d#'", inp1, inp2, inp3);
keyPort.button('*').send();
keyPort.button(toChar(inp1)).send();
keyPort.button(toChar(inp2)).send();
keyPort.button(toChar(inp3)).send();
keyPort.button('#').send();
command parameter: -UARGS rand or -UARGS r

Part 4 
1.	The scenario is shown as ‘LockAndCracking’ in the state machine.
In this scenario, the harness will try to unlock the safe with all valid 3-digit codes from ‘000’ to ’999’ until either 
the safe is opened, or all valid 3-digit codes have been tried. Here the initial locking code is hard coded to ‘008’, 
so the harness will try ‘000’, ‘001’,…, ‘008’. 
Run with the following argument:
command parameter: -UARGS c

2.	The scenario is shown as ‘Input’ and ‘Cracking’ in the state machine.
The harness will first lock the safe with the code from argument (take ‘123’ as example) and then crack the safe trying all 
valid 3-digit codes starting from ‘000’ until ‘123’ where the lock is opened. 
Run with the following argument:
command parameter: -UARGS 123c

3.	The scenario is shown as ‘Random’ and ‘Cracking’ in the state machine. 
The harness will first lock the safe with the code randomly generated and then crack the safe trying all valid 3-digit codes 
until either the safe is opened, or all valid 3-digit codes have been tried.
Run with the following argument:
command parameter: -UARGS rc