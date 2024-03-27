<template>
    <div>
        <div class="container-fluid pt-3">
            <div class="card">
                <div class="card-header">
                    Schmerz-Intensität
                </div>
                <div>
                    <canvas id="pain-chart" width="600" height="400"></canvas>
                </div>
            </div>
        </div>

        <div class="container-fluid pt-3">
            <div class="card">
                <div class="card-header">
                    Lebensbäume
                </div>
                <div class="card-body" >
                    <div class="container">
                        <div class="row text-center" >
                            <div class="col-xs-4">
                                <img src="img/stress_small/tief_0.svg" width="100" />
                            </div>
                            <div class="col-xs-4" >
                                <img src="img/stress_small/tief_0.svg" width="100" />
                            </div>
                            <div class="col-xs-4">
                                <img src="img/stress_small/tief_0.svg" width="100" />
                            </div>
                            <div class="col-xs-4">
                                <img src="img/stress_small/tief_0.svg" width="100" />
                            </div>
                            <div class="col-xs-4">
                                <img src="img/stress_small/tief_0.svg" width="100" />
                            </div>
                            <div class="col-xs-4">
                                <img src="img/stress_small/tief_0.svg" width="100" />
                            </div>
                            <div class="col-xs-4">
                                <img src="img/stress_small/tief_0.svg" width="100" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

import { Chart, ChartConfiguration, LineController, LineElement, PointElement, LinearScale, Title, CategoryScale } from 'chartjs';
Chart.register(LineController, LineElement, PointElement, LinearScale, Title, CategoryScale);





onMounted(() => {
    axios.get('/api/overview/painPlot').then(responsePain => {
        let pain = responsePain.data;
        axios.get('/api/overview/stressPlot').then(responseStress => {
            let stress = responseStress.data;
            Chart.defaults.plugins.legend = {
                enabled: true
            };
            new Chart(document.getElementById('pain-chart'), {
                type: "line",
                data: {
                    labels: pain.labels,
                    datasets: [
                        {
                            label: "Pain",
                            data: pain.data,
                            borderColor: "red"
                        },
                        {
                            label: "Stress",
                            data: stress.data,
                            borderColor: "blue"
                        }
                    ]
                },
                options: {
                    plugins: {
                        legend: {
                            display: true, // This makes sure the legend is displayed
                            position: 'bottom', // This positions the legend at the bottom of the chart
                            labels: {
                                font: { // This customizes the legend's label font
                                    size: 14,
                                    weight: 'italic'
                                },
                                padding: 10 // Moved padding outside of font, directly under labels
                            }
                        }
                    }
                }
            })
        });

    });
});



</script>

<style> /* The heart of the matter */

 .horizontal-scrollable>.row {
   overflow-x:auto
 }

 .horizontal-scrollable>.row>.col-xs-4 {

 }

 /* Decorations */

 .col-xs-4 {
     max-width: 130px;
 }

 .col-xs-4:nth-child(2n+1) {
     background: green;
 }

 .col-xs-4:nth-child(2n+2) {
     background: black;
 }
</style> 