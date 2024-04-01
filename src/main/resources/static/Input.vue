<template>
    <div class="container-fluid pt-3">
        <div class="card">
            <div class="card-header" style="background-color: grey;">
                Meine Angaben
            </div>
            <div class="card-body" style="background-color: lightgrey;">
                <div class="mb-3">
                    <label for="timeOfDay" class="form-label" style="font-weight: 500;">Zeitpunkt</label>
                    <select class="form-select" v-model="selectionModel.timeOfDay" aria-label="Default select example">
                        <option value="UNSET">keine Angabe</option>
                        <option value="MORNING">Morgen</option>
                        <option value="AFTERNOON">Nachmittag</option>
                        <option value="EVENING">Abend</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="painIntensity" class="form-label" style="font-weight: 500;">Schmerz-Intensität</label>
                    <select class="form-select" aria-label="Default select example" v-model="selectionModel.intensity">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="painSymptoms" class="form-label" style="font-weight: 500;">Symptome</label>
                    <div class="form-check" v-bind:key="cm.name" v-for="cm in selectionModel.symptoms">
                        <input class="form-check-input" type="checkbox" v-model="cm.selected" id="flexCheckDefault">
                        <label class="form-check-label" for="flexCheckDefault">
                            {{ cm.name }}
                        </label>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="stressLevel" class="form-label" style="font-weight: 500;">Stress-Level</label>
                    <select class="form-select" aria-label="Default select example"
                        v-model="selectionModel.stressLevel">
                        <option value="0">kein</option>
                        <option value="1">tief</option>
                        <option value="2">mittel</option>
                        <option value="3">hoch</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="stressStressors" class="form-label" style="font-weight: 500;">Stressoren</label>
                    <div v-bind:key="category" v-for="category in stressorSelectModel.keys()">
                        <b style="font-weight: 400; font-style: italic;">{{ category }}</b>
                        <div class="form-check" v-bind:key="cm.name" v-for="cm in stressorSelectModel.get(category)">
                            <input class="form-check-input" type="checkbox" v-model="cm.selected" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                {{ cm.name }}
                            </label>
                        </div>
                    </div>
                </div>
                <div class="card-footer">
                    <div class="d-flex justify-content-between align-items-center">
                        <button type="button" class="btn btn-primary" style="background-color: lightseagreen;border-color: lightseagreen; outline: none;" @click="saveSituation">Eingaben speichern</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script setup>
import { ref, onMounted } from 'vue';

const selectionModel = ref([]);
const stressorSelectModel = ref(new Map());

onMounted(() => {
    axios.get('/api/situation/new/empty').then(response => {
        selectionModel.value = response.data;




        response.data.stressors.forEach(stressor => {
            if (!stressorSelectModel.value.has(stressor.category)) {
                stressorSelectModel.value.set(stressor.category, []);
            }
            stressorSelectModel.value.get(stressor.category).push(stressor);

        });

    });
});

const saveSituation = () => {

    const symtomsSelected = [];
    stressorSelectModel.value.forEach((value, key) => {
        stressorSelectModel.value.get(key).forEach(stressor => {
            symtomsSelected.push(stressor);
        });
    });
    selectionModel.value.stressors = symtomsSelected;


    axios.post('/api/situation', selectionModel.value).then(response => {
        window.location.href = '#/';
    });
};

</script>