<template>
  <aside class="flex overflow-x-auto border-b border-gray-900/5 py-4 lg:block lg:w-64 lg:flex-none lg:border-0 lg:py-20">
    <fieldset>
      <legend class="block text-sm font-medium leading-6 text-gray-900">
        Search
      </legend>
      <div class="mt-2 -space-y-px rounded-md bg-white shadow-sm">
        <div>
          <label
            for="search"
            class="sr-only"
          />
          <input
            id="search"
            type="text"
            name="search"
            class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            placeholder="Enter a search term"
          >
        </div>
      </div>
    </fieldset>
    <fieldset class="mt-6 bg-white">
      <legend class="block text-sm font-medium leading-6 text-gray-900">
        Price
      </legend>
      <div class="mt-2 -space-y-px rounded-md shadow-sm">
        <div>
          <label
            for="min-price"
            class="sr-only"
          >Min price</label>
          <div class="relative mt-2 rounded-md shadow-sm">
            <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
              <span class="text-gray-500 sm:text-sm">$</span>
            </div>
            <input
              v-model="minPrice"
              id="min-price"
              type="text"
              name="min-price"
              class="block w-full rounded-md border-0 py-1.5 pl-7 pr-12 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              placeholder="0.00"
              aria-describedby="price-currency"
            >
            <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3">
              <span
                id="price-currency"
                class="text-gray-500 sm:text-sm"
              >USD</span>
            </div>
          </div>
        </div>
        <div>
          <label
            for="max-price"
            class="sr-only"
          >Max price</label>
          <div class="relative mt-2 rounded-md shadow-sm">
            <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
              <span class="text-gray-500 sm:text-sm">$</span>
            </div>
            <input
              v-model="maxPrice"
              id="max-price"
              type="text"
              name="max-price"
              class="block w-full rounded-md border-0 py-1.5 pl-7 pr-12 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              placeholder="0.00"
              aria-describedby="price-currency"
            >
            <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3">
              <span
                id="price-currency"
                class="text-gray-500 sm:text-sm"
              >USD</span>
            </div>
          </div>
        </div>
      </div>
    </fieldset>
    <fieldset class="mt-6 bg-white">
      <legend class="block text-sm font-medium leading-6 text-gray-900">
        Date
      </legend>
      <div class="mt-2 -space-y-px rounded-md shadow-sm">
        <div>
          <label
            for="min-date"
            class="sr-only"
          >Min date</label>
          <div class="relative mt-2 rounded-md shadow-sm">
            <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
              <span class="text-gray-500 sm:text-sm">$</span>
            </div>
            <input
              id="min-date"
              type="text"
              name="min-date"
              class="block w-full rounded-md border-0 py-1.5 pl-7 pr-12 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              placeholder="0.00"
              aria-describedby="date-currency"
            >
            <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3">
              <span
                id="date-currency"
                class="text-gray-500 sm:text-sm"
              >USD</span>
            </div>
          </div>
        </div>
        <div>
          <label
            for="max-date"
            class="sr-only"
          >Max date</label>
          <div class="relative mt-2 rounded-md shadow-sm">
            <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
              <span class="text-gray-500 sm:text-sm">$</span>
            </div>
            <input
              id="max-date"
              type="text"
              name="max-date"
              class="block w-full rounded-md border-0 py-1.5 pl-7 pr-12 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              placeholder="0.00"
              aria-describedby="date-currency"
            >
            <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3">
              <span
                id="date-currency"
                class="text-gray-500 sm:text-sm"
              >USD</span>
            </div>
          </div>
        </div>
      </div>
    </fieldset>
    <fieldset class="mt-6 bg-white">
      <legend class="block text-sm font-medium leading-6 text-gray-900">
        Genre
      </legend>
      <div class="mt-2 -space-y-px rounded-md bg-white shadow-sm">
        <div>
          <label
            for="genre"
            class="sr-only"
          />
          <input
            id="genre"
            type="text"
            name="genre"
            class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            placeholder="Enter a search term"
          >
        </div>
      </div>
    </fieldset>

    <div class="mt-5">
      <button
        type="button"
        class="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500"
        @click="loadEvents"
        >
        Search
      </button>
    </div>
  </aside>

  <main class="px-4 py-16 sm:px-6 lg:flex-auto lg:px-0 lg:py-20">
    <div class="mx-auto max-w-2xl space-y-16 sm:space-y-20 lg:mx-0 lg:max-w-none">
      <Event
        v-for="event in events"
        :key="event.id"
        :event="event"
      />
    </div>
  </main>
</template>

<script setup>
import Event from './Event.vue'
import { ref, onMounted } from 'vue'

const events = ref([])

const minPrice = ref(null)
const maxPrice = ref(null)

const loadEvents = _ => {
  const baseUrl = 'http://localhost:8080/api/events'

  const params = {}
  if (minPrice.value) params.min_price = minPrice.value
  if (maxPrice.value) params.max_price = maxPrice.value

  const url = baseUrl + '?' + Object.keys(params).map(key => `${key}=${encodeURIComponent(params[key])}`).join('&')

  fetch(url)
    .then(response => response.json())
    .then(data => {
      events.value = data
    })
}

onMounted(() => {
  loadEvents()
})

</script>
