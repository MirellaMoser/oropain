<template>
    <!-- Container for the form -->
    <div class="container-fluid pt-3">
      <!-- Card to display user input fields -->
      <div class="card" v-if="availabelTimeSlots.length !== 0">
        <div class="card-header" style="background-color: grey;">
          Meine Angaben
        </div>
        <div class="card-body" style="background-color: lightgrey;">
          <!-- Time of Day selection -->
          <div class="mb-3">
            <label for="timeOfDay" class="form-label" style="font-weight: 500;">Zeitpunkt</label>
            <select class="form-select" v-model="selectionModel.timeOfDay" aria-label="Default select example">
              <option v-for="slot in availabelTimeSlots" :key="slot.value" :value="slot.value">{{ slot.label }}</option>
            </select>
          </div>
          <!-- Pain Intensity selection -->
          <div class="mb-3">
            <label for="painIntensity" class="form-label" style="font-weight: 500;">Schmerz-Intensität</label>
            <select class="form-select" aria-label="Default select example" v-model="selectionModel.intensity">
              <option v-for="i in 11" :key="i" :value="i - 1">{{ i - 1 }}</option>
            </select>
          </div>
          <!-- Symptom checkboxes -->
          <div class="mb-3">
            <label for="painSymptoms" class="form-label" style="font-weight: 500;">Symptome</label>
            <div class="form-check" v-for="symptom in selectionModel.symptoms" :key="symptom.name">
              <input class="form-check-input" type="checkbox" v-model="symptom.selected" id="flexCheckDefault">
              <label class="form-check-label" for="flexCheckDefault">{{ symptom.name }}</label>
            </div>
          </div>
          <!-- Stress Level selection -->
          <div class="mb-3">
            <label for="stressLevel" class="form-label" style="font-weight: 500;">Stress-Level</label>
            <select class="form-select" aria-label="Default select example" v-model="selectionModel.stressLevel">
              <option value="0">kein</option>
              <option value="1">tief</option>
              <option value="2">mittel</option>
              <option value="3">hoch</option>
            </select>
          </div>
          <!-- Stressor checkboxes grouped by category -->
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
          <!-- Save button -->
          <div class="card-footer">
            <div class="d-flex justify-content-between align-items-center">
              <button type="button" class="btn btn-primary" style="background-color: lightseagreen;border-color: lightseagreen; outline: none;" @click="saveSituation">Eingaben speichern</button>
            </div>
          </div>
        </div>
      </div>
      <!-- Message for no available time slots -->
      <div v-else>
        Keine weiteren Einträge für diesen Tag möglich.
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const selectionModel = ref([]);
  const availabelTimeSlots = ref([]);
  const stressorSelectModel = ref(new Map());
  
  onMounted(() => {
    axios.get('/api/situation/empty').then(response => {
      // Assigning response data to selectionModel and availabelTimeSlots
      selectionModel.value = response.data;
      availabelTimeSlots.value = selectionModel.value.availableEntries.map(element => {
        // Mapping available time slots to labels
        if (element === 'MORNING')
          return { label: 'Morgen', value: element };
        else if (element === 'AFTERNOON')
          return { label: 'Nachmittag', value: element };
        else if (element === 'EVENING')
          return { label: 'Abend', value: element };
        else {
          return element;
        }
      });
  
      // Setting default time of day if available
      if (availabelTimeSlots.value.length > 0) {
        selectionModel.value.timeOfDay = availabelTimeSlots.value[0].value;
      }
  
      // Grouping stressors by category
      response.data.stressors.forEach(stressor => {
        if (!stressorSelectModel.value.has(stressor.category)) {
          stressorSelectModel.value.set(stressor.category, []);
        }
        stressorSelectModel.value.get(stressor.category).push(stressor);
      });
    });
  });
  
  const saveSituation = () => {
    const selectedStressors = [];
    stressorSelectModel.value.forEach((value, key) => {
      stressorSelectModel.value.get(key).forEach(stressor => {
        selectedStressors.push(stressor);
      });
    });
    selectionModel.value.stressors = selectedStressors;
  
    // Saving situation via API call
    axios.post('/api/situation', selectionModel.value).then(response => {
      window.location.href = '#/';
    });
  };
  </script>