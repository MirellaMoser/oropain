<template>
    <!-- Container for charts and modal -->
    <div>
        <!-- Pain and Stress chart -->
        <div class="container-fluid pt-3">
            <div class="card">
                <div class="card-header">
                    Übersicht Schmerz <span style="color: red;">(rot)</span> und Stress <span
                        style="color: blue;">(blau)</span>
                </div>
                <div>
                    <canvas id="pain-chart" width="600" height="400"></canvas>
                </div>
            </div>
        </div>

        <!-- Tree Descriptors -->
        <div class="container-fluid pt-3">
            <div class="card">
                <div class="card-header">
                    Lebensbäume
                </div>
                <div class="card-body">
                    <div class="container">
                        <div class="row text-center horizontal-scrollable">
                            <!-- Iterate over tree descriptors -->
                            <div class="col-xs-4" v-for="tree in treeDescriptor" :key="tree.date">
                                <a href="#" data-bs-toggle="modal" data-bs-target="#treeinfo"
                                    @click="modalTreeClicked(tree)">
                                    <div style="height:100px; position: relative;">
                                        <img :src="tree.imagePath"
                                            style="height: 100%; max-width: 100%; position: absolute; top: 0; left: 0;" />
                                        <img :src="tree.overlayPath"
                                            style="height: 100%; max-width: 100%; position: absolute; top: 0; left: 0;" />
                                    </div>
                                </a>
                                <div style="margin-top: 5px;">{{ tree.date }}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal for displaying tree info -->
        <div class="modal fade" id="treeinfo" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Ihr Lebensbaum</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Display current tree descriptor data -->
                        <div>{{ currentTreeDescriptor.date }}</div>
                        <div class="table-responsive">
                            <table class="table" style="font-size: 11px;">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <!-- Display time of day for each situation -->
                                        <th v-for="situation in currentTreeDescriptor.situations" :key="situation">{{
                                            situation.timeOfDay }}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Display pain level for each situation -->
                                    <tr>
                                        <th scope="row">Schmerz-Intensität</th>
                                        <td v-for="situation in currentTreeDescriptor.situations" :key="situation">{{
                                            situation.painLevel }}</td>
                                    </tr>
                                    <!-- Display stress level for each situation -->
                                    <tr>
                                        <th scope="row">Stress-Level</th>
                                        <td v-for="situation in currentTreeDescriptor.situations" :key="situation">{{
                                            situation.stressLevel }}</td>
                                    </tr>
                                    <!-- Display symptoms for each situation -->
                                    <tr>
                                        <th scope="row">Symptome</th>
                                        <td v-for="situation in currentTreeDescriptor.situations" :key="situation">
                                            <ul style="list-style-type: none; padding-left: 0; margin-left: 0;">
                                                <li v-for="symptom in situation.symptoms" :key="symptom">{{ symptom.name }}
                                                </li>
                                            </ul>
                                        </td>
                                    </tr>
                                    <!-- Display stressors for each situation -->
                                    <tr>
                                        <th scope="row">Stressoren</th>
                                        <td v-for="situation in currentTreeDescriptor.situations" :key="situation">
                                            <ul style="list-style-type: none; padding-left: 0; margin-left: 0;">
                                                <li v-for="stressor in situation.stressors" :key="stressor">{{ stressor.name
                                                }}</li>
                                            </ul>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <!-- Close button for modal -->
                        <button type="button" class="btn btn-primary"
                            style="background-color: lightseagreen; border-color: lightseagreen; outline: none;"
                            data-bs-dismiss="modal">Schliessen</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { ref, onMounted } from 'vue';
import { Chart, LineController, LineElement, PointElement, LinearScale, Title, CategoryScale } from 'chartjs';

// Register chart elements and scales
Chart.register(LineController, LineElement, PointElement, LinearScale, Title, CategoryScale);

// Initialize reactive variables
const treeDescriptor = ref([]);
const currentTreeDescriptor = ref();
const dailyRecords = ref([]);

currentTreeDescriptor.value = {
    date: "",
    situations: [],
};

// Fetch data from APIs on component mount
onMounted(() => {
    axios.get('/api/plot/pain').then(responsePain => {
        // Process pain data
        let pain = responsePain.data;
        axios.get('/api/plot/stress').then(responseStress => {
            // Process stress data
            let stress = responseStress.data;
            // Create and configure chart
            new Chart(document.getElementById('pain-chart'), {
                type: "line",
                data: {
                    labels: pain.labels,
                    datasets: [
                        { label: "Pain", data: pain.data, borderColor: "red" },
                        { label: "Stress", data: stress.data, borderColor: "blue" }
                    ]
                },
                options: {
                    plugins: {
                        legend: {
                            display: true,
                            position: 'bottom',
                            labels: {
                                font: { size: 14, weight: 'italic' },
                                padding: 10
                            }
                        }
                    }
                }
            });
        });
    });

    axios.get('/api/record').then(responseStress => {


        // Process daily records
        let allRecords = responseStress.data;
        dailyRecords.value = allRecords;

        allRecords.forEach(record => {
            // Process each record
            let selectedCms = record.counterMeasures.length;
            let imagePath = "";
            let overlayPath = "";
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
        });
    });
});

// Function to handle click on tree image
const modalTreeClicked = (tree) => {
    currentTreeDescriptor.value.date = tree.date;
    // Find corresponding daily record
    dailyRecords.value.forEach(dr => {
        if (dr.dateTime === tree.date) {
            currentTreeDescriptor.value.situations = dr.situations;
        }
    });
};
</script>
  
<style>
/* Additional styles for horizontal scroll */
.horizontal-scrollable>.row {
    overflow-x: auto;
}

/* Styles for card column width */
.col-xs-4 {
    max-width: 130px;
}
</style>
  