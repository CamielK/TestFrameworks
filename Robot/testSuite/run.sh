#!/bin/sh
# description: 	This file contains the startup script for the workflow4people robot framework test suite. 
#		It executes the various test scripts (.robot files) and post processes the test output files.
#		Script allows for either the frontend, backend or both to be tested depending on the given parameter.


# Check for correct script parameters. Can take 1 or 0 parameters
if [ "$#" = "0" ] #no argument = execute both
then
   runFrontendSuite=true
   runBackendSuite=true
elif [ "$1" = "frontend" ] #execute only frontend
then
   runFrontendSuite=true
   runBackendSuite=false
elif [ "$1" = "backend" ] #execute only backend
then
   runFrontendSuite=false
   runBackendSuite=true
else #wrong argument > throw exception and stop script.
   echo "Error running test suite: script argument must be 'frontend', 'backend' or no argument"
   return
fi


#list of test files >> executed in this order, add new test scripts here
declare -a frontendArr=(
"Login"
)
declare -a backendArr=(
"Login"
"MakeUser"
)


#create main output dir for this test run
now=$(date +"%m-%d-%Y-%T")
nowString="${now//:}" #parse ':' out of string for windows compatibility
newDir="testOutputs/$nowString"
mkdir -p "$newDir"


#combines test outputs to create single test suite output per interface
combine_outputs () {
   rebot --name $1 "$newDir/$1/xmlfiles/*.xml"
   mv "report.html" "$newDir/$1"
   mv "log.html" "$newDir/cd$1"
   echo ""
   echo "complete $1 test suite result can be found at $newDir/$1/report.html"
   echo ""
}


#move test output files to new dir
move_outputs () {
   #$1 = test name
   #$2 = frontend or backend
   testDir="$newDir/$2/$1" #directory with current test results
   xmlDir="$newDir/$2/xmlfiles" #directory with collection of xml results
   mkdir -p "$testDir" 
   mkdir -p "$xmlDir" 
   mv "output.xml" "($1)output.xml" #rename output file to unique name
   mv "($1)output.xml" "$xmlDir"
   mv "report.html" "$testDir"
   mv "log.html" "$testDir"
   echo "Output files for this test run were moved to $testDir"
}


#run wfp frontend tests
if [ "$runFrontendSuite" = true ]
then
   for i in "${frontendArr[@]}"
   do
      pybot testScripts/frontend/$i.robot
      move_outputs $i "frontend"
   done
   combine_outputs "frontend"
fi

#run wfp backend tests
if [ "$runBackendSuite" = true ]
then
   for i in "${backendArr[@]}"
   do
      pybot testScripts/backend/$i.robot
      move_outputs $i "backend"
   done
   combine_outputs "backend"
fi

