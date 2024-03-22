<template>
    <div>
        <div class="container-fluid pt-3">
            <div class="card">
                <div class="card-header">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Meine Angaben
                        <span>{{ overview.dateOfEntry }}</span>
                    </li>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Schmerz-Intensität
                        <span>{{ overview.intensity }}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Symptome
                        <span>{{ overview.symptoms }}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Stress-Level
                        <span>{{ overview.stressLevel }}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Stressoren
                        <span>{{ overview.stressors }}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Zeitpunkt
                        <span v-if="overview.timeOfEntry === 'UNSET'">Keine Angabe</span>
                        <span v-if="overview.timeOfEntry === 'MORNING'">Morgen</span>
                        <span v-if="overview.timeOfEntry === 'AFTERNOON'">Nachmittag</span>
                        <span v-if="overview.timeOfEntry === 'EVENING'">Abend</span>
                        <span v-if="overview.timeOfEntry === 'NIGHT'">Nacht</span>
                    </li>
                </ul>
                <div style="position:relative;">
                    <div style="position: absolute;">
                        <a href="#" data-bs-toggle="modal" data-bs-target="#baumerweiterung">
                            <img :src="imagePath" style="width: 100%; max-width: 100%;">
                        </a>
                    </div>
                    <div style="position: absolute; top: 0px;">
                        <a href="#" data-bs-toggle="modal" data-bs-target="#baumerweiterung">
                            <img :src="overlayPath" style="width: 100%; max-width: 100%;">
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="baumerweiterung" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Lebensbaum erweitern und Massnahmen hinzufügen
                        </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-check" v-bind:key="cm.name" v-for="cm in countermeasuresOptions">
                            <input class="form-check-input" type="checkbox" v-model="cm.selected" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                {{ cm.name }}
                            </label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal"
                            @click="saveCountermeasures">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const countermeasuresOptions = ref([]);
const overview = ref([]);
const imagePath = ref();
const overlayPath = ref();


onMounted(() => {
    axios.get('/api/situation/current/countermeasures').then(response => {
        countermeasuresOptions.value = response.data;
        updateOverviewImage();
    });
    axios.get('/api/situation/current/overview').then(response => {
        overview.value = response.data;
        updateOverviewImage();
        console.log(response.data);

    });
});

const saveCountermeasures = () => {
    axios.post('/api/situation/current/countermeasures', countermeasuresOptions.value).then(response => {
        updateOverviewImage();
    });
};

const updateOverviewImage = () => {

    var selectedCms = 0;
    for(const cm of countermeasuresOptions.value) {
        if(cm.selected) selectedCms ++;
    }
    if(selectedCms > 3) selectedCms = 3;

    if(overview.value.stressLevel === undefined) return;

    if(overview.value.intensity === -1) overview.value.intensity = 0;

    if (overview.value.stressLevel === "kein") {
        imagePath.value = "/img/stress_null/kein_" + overview.value.intensity + ".svg";
        overlayPath.value = "/img/stress_null/overlay_Stufe"+selectedCms+".svg";
    }
    else if (overview.value.stressLevel === "tief") {
        imagePath.value = "/img/stress_small/tief_" + overview.value.intensity + ".svg";
        overlayPath.value = "/img/stress_small/overlay_Stufe"+selectedCms+".svg";
    }
    else if (overview.value.stressLevel === "mittel") {
        imagePath.value = "/img/stress_medium/mittel_" +overview.value.intensity + ".svg";
        overlayPath.value = "/img/stress_medium/overlay_Stufe"+selectedCms+".svg";
    }
    else {
        imagePath.value = "/img/stress_high/hoch_" + overview.value.intensity + ".svg";
        overlayPath.value = "/img/stress_high/overlay_Stufe"+selectedCms+".svg";
    }
};
</script>