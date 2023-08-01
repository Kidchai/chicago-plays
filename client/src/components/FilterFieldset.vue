<template>
  <fieldset class="mt-6 bg-white">
    <legend class="block text-sm font-medium leading-6 text-gray-900">
      {{ label }}
    </legend>
    <div class="mt-2 -space-y-px rounded-md bg-white shadow-sm">
      <slot />
    </div>
  </fieldset>
</template>

<script setup>
import { defineProps } from 'vue'

defineProps({
  label: {
    type: String,
    required: true,
  },
})
</script>
