<template>
    <div class="container-fluid pt-3">
        <div class="card" style="width: 18rem;">
            <div class="card-header">
                Schmerz-Intensität
            </div>
            <ul class="list-group list-group-flush">
                <div>
                    <canvas id="pain-chart"></canvas>
                </div>
            </ul>
        </div>
    </div>
    <div class="container-fluid pt-3">
        <div class="card" style="width: 18rem;">
            <div class="card-header">
                Stress-Level
            </div>
            <ul class="list-group list-group-flush">
                <div>
                    <canvas id="stress-chart"></canvas>
                </div>
            </ul>
        </div>
    </div>

    <div class="container-fluid pt-3">
        <div class="card" style="width: 18rem;">
            <div class="card-header">
                Lebensbäume
            </div>
            <div class="container text-center">
                <div class="row">
                    <div class="col">
                        <img src="img/Baum2.svg" class="img-fluid" alt="...">
                    </div>
                    <div class="col">
                        <img src="img/Baum3.svg" class="img-fluid" alt="...">
                    </div>
                    <div class="col">
                        <img src="img/Baum3.svg" class="img-fluid" alt="...">
                    </div>
                </div>
            </div>
            <div class="container text-center">
                <div class="row">
                    <div class="col">
                        <img src="img/Baum4.svg" class="img-fluid" alt="...">
                    </div>
                    <div class="col">
                        <img src="img/Baum5.svg" class="img-fluid" alt="...">
                    </div>
                    <div class="col">
                        <img src="img/Baum5.svg" class="img-fluid" alt="...">
                    </div>
                </div>
            </div>
            <div class="container text-center">
                <div class="row">
                    <div class="col">
                        <img src="img/Baum2.svg" class="img-fluid" alt="...">
                    </div>
                    <div class="col">
                        <img src="img/Baum4.svg" class="img-fluid" alt="...">
                    </div>
                    <div class="col">
                        <img src="img/Baum1.svg" class="img-fluid" alt="...">
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
    axios.get('/api/overview/painPlot').then(response => {
        let data = response.data;
        new Chart(document.getElementById('pain-chart'), {
            type: "line",
            data: {
                labels: data.labels,
                datasets: [
                    {
                        label: "Pain",
                        data: data.data
                    }
                ]
            }
        })
    });

    axios.get('/api/overview/stressPlot').then(response => {
        let data = response.data;
        new Chart(document.getElementById('stress-chart'), {
            type: "line",
            data: {
                labels: data.labels,
                datasets: [
                    {
                        label: "Stress",
                        data: data.data
                    }
                ]
            }
        })
    });

});



</script>