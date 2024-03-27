<template>
    <div class="container-fluid pt-3">
        <div class="card">
            <div class="card-header">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Meine Angaben
                    <input type="date" class="form-control" id="dateInput" name="date" value="2024-03-01">
                </li>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Schmerz-Intensität
                    <span>
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
                    </span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Symptome
                    <span>
                        <div class="form-check" v-bind:key="cm.name" v-for="cm in selectionModel.symptoms">
                            <input class="form-check-input" type="checkbox" v-model="cm.selected" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                {{ cm.name }}
                            </label>
                        </div>
                    </span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Stress-Level
                    <span>
                        <select class="form-select" aria-label="Default select example"
                            v-model="selectionModel.stressLevel">
                            <option value="0">kein</option>
                            <option value="1">tief</option>
                            <option value="2">mittel</option>
                            <option value="3">hoch</option>                            
                        </select>
                    </span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Stressoren
                    <span>
                        <div v-bind:key="category" v-for="category in stressorSelectModel.keys()">
                            <b>{{ category }}</b>
                            <div class="form-check" v-bind:key="cm.name" v-for="cm in stressorSelectModel.get(category)">
                                <input class="form-check-input" type="checkbox" v-model="cm.selected" id="flexCheckDefault">
                                <label class="form-check-label" for="flexCheckDefault">
                                    {{ cm.name }}
                                </label>
                            </div>
                        </div>
                    </span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Zeitpunkt
                    <span><select class="form-select" v-model="selectionModel.timeOfDay"
                            aria-label="Default select example">
                            <option value="UNSET">keine Angabe</option>
                            <option value="MORNING">Morgen</option>
                            <option value="AFTERNOON">Nachmittag</option>
                            <option value="EVENING">Abend</option>
                        </select></span>
                </li>
            </ul>
            <div class="card-footer">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <button type="button" class="btn btn-primary" @click="saveSituation">Save changes</button>
                </li>
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
        stressorSelectModel.value.forEach((value,key) => {            
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