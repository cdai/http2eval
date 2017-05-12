#!/bin/bash

reportdat="report-loss.dat"
reportdat2="report-rtt.dat"
reportdat3="report-bw.dat"

if [ -f $reportdat ]; then
    echo "Clean old report file $reportdat, $reportdat2, $reportdat3"
    rm -f $reportdat $reportdat2 $reportdat3
fi

# Loss rate
lossrates="0% 1.0% 1.5%"
for lossrate in $lossrates
do
    echo "Start to test with loss rate $lossrate"
    sudo tc qdisc del dev lo root netem
    sudo tc qdisc add dev lo root netem delay 20ms loss $lossrate
    ./gradlew run -Pmyargs="['$reportdat', 'Loss rate $lossrate']" > /dev/null
    echo "Complete!"
done

# Round-trip time
rtts="20ms 100ms 200ms"
for rtt in $rtts
do
    echo "Start to test with RTT $rtt"
    sudo tc qdisc del dev lo root netem
    sudo tc qdisc add dev lo root netem delay $rtt
    ./gradlew run -Pmyargs="['$reportdat2', 'RTT $rtt']" > /dev/null
    echo "Complete!"
done

# Bandwidth
bws="10mbit 1mbit"
for bw in $bws
do
    echo "Start to test with bandwidth $bw"
    sudo tc qdisc del dev lo root netem
    sudo tc qdisc add dev lo root netem delay 20ms rate $bw
    ./gradlew run -Pmyargs="['$reportdat3', 'Bandwidth $bw']" > /dev/null
    echo "Complete!"
done

cp $reportdat $reportdat2 $reportdat3 ../server/
