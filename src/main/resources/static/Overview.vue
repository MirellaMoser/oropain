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
                <div class="card-body">
                    <div class="container">
                        <div class="row text-center">
                            <div class="col-xs-4" v-for="tree in treeDescriptor" v-bind:key="tree">
                                <a href="#" data-bs-toggle="modal" data-bs-target="#treeinfo"
                                    v-on:click.prevent="modalTreeClicked(tree)">
                                    <div style="height:100px;">
                                        <img :src="tree.imagePath" style="width: 100%; max-width: 100%;" />
                                        <img :src="tree.overlayPath"
                                            style="width: 100%; max-width: 100%; transform: translateY(-100%);" />
                                    </div>
                                </a>
                                {{ tree.date }}

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade" id="treeinfo" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Ihr toller Baum
                        </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        {{ currentTreeDescriptor.date }}
                        <div>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th scope="col" v-for="situation in currentTreeDescriptor.situations"
                                            v-bind:key="situation">{{ situation.timeOfDay }}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th scope="row">Schmerzintsnsität</th>
                                        <td v-for="situation in currentTreeDescriptor.situations" v-bind:key="situation">
                                            {{ situation.painLevel }}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Stresslevel</th>
                                        <td v-for="situation in currentTreeDescriptor.situations" v-bind:key="situation">
                                            {{ situation.stressLevel }}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Symptome</th>
                                        <td v-for="situation in currentTreeDescriptor.situations" v-bind:key="situation">
                                            <ul>
                                                <li v-for="symptom in situation.symptoms" v-bind:key="symptom">{{
                                                    symptom.name }}</li>
                                            </ul>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Stressoren</th>
                                        <td v-for="situation in currentTreeDescriptor.situations" v-bind:key="situation">
                                            <ul>
                                                <li v-for="stressor in situation.stressors" v-bind:key="stressor">{{
                                                    stressor.name }}</li>
                                            </ul>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Schliessen</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

import { Chart, ChartConfiguration, LineController, LineElement, PointElement, LinearScale, Title, CategoryScale } from 'chartjs';


Chart.register(LineController, LineElement, PointElement, LinearScale, Title, CategoryScale);


const treeDescriptor = new ref([]);
const currentTreeDescriptor = new ref();
const dailyRecords = new ref([]);

currentTreeDescriptor.value = {
    date: "",
    situations: [],
};

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
            });

        });

    });

    axios.get('/api/overview/dailyRecords').then(responseStress => {
        let allRecords = responseStress.data;
        dailyRecords.value = allRecords;
        let selectedCms = 0;
        let imagePath = "";
        let overlayPath = "";

        allRecords.forEach(record => {
            selectedCms = record.counterMeasures.length;
            if (selectedCms > 3) {
                selectedCms = 3;
            }
            let stressLevelRounded = Math.round(record.averageStressLevel);
            let painLevelRounded = Math.round(record.averagePainLevel);
            if (stressLevelRounded === 0) {
                imagePath = "/img/stress_null/kein_" + painLevelRounded + ".svg";
                overlayPath = "/img/stress_null/overlay_Stufe" + selectedCms + ".svg";
            }
            else if (stressLevelRounded === 1) {
                imagePath = "/img/stress_small/tief_" + painLevelRounded + ".svg";
                overlayPath = "/img/stress_small/overlay_Stufe" + selectedCms + ".svg";
            }
            else if (stressLevelRounded === 2) {
                imagePath = "/img/stress_medium/mittel_" + painLevelRounded + ".svg";
                overlayPath = "/img/stress_medium/overlay_Stufe" + selectedCms + ".svg";
            }
            else {
                imagePath = "/img/stress_high/hoch_" + painLevelRounded + ".svg";
                overlayPath = "/img/stress_high/overlay_Stufe" + selectedCms + ".svg";
            }


            treeDescriptor.value.push({
                date: record.dateTime,
                imagePath: imagePath,
                overlayPath: overlayPath
            });
        })

        console.log(treeDescriptor.value);

    });
});

const modalTreeClicked = (tree) => {

    currentTreeDescriptor.value.date = tree.date;

    dailyRecords.value.forEach(dr => {
        if (dr.dateTime === tree.date) {
            currentTreeDescriptor.value.situations = dr.situations;

        }
    })


    console.log(currentTreeDescriptor.value);

}

</script>

<style> /* The heart of the matter */

 .horizontal-scrollable>.row {
     overflow-x: auto
 }


 /* Decorations */

 .col-xs-4 {
     max-width: 130px;
 }</style> 