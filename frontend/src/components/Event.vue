<template>
  <article
    class="flex max-w-xl flex-col items-start justify-between"
  >
    <div class="flex items-center gap-x-4 text-xs">
      <span class="text-gray-500">
        <time :datetime="event.firstDate">{{ event.firstDate }}</time>
        -
        <time :datetime="event.lastDate">{{ event.lastDate }}</time>
      </span>
      <a v-for="genre in event.genres" :key="genre.id"
        class="relative z-10 rounded-full bg-gray-50 px-3 py-1.5 font-medium text-gray-600 hover:bg-gray-100"
      >{{ genre.genre }}</a>

      <span v-if="event.minPrice || event.maxPrice" class="text-gray-500">
        <template v-if="event.minPrice">from ${{ event.minPrice }}</template>
        <template v-if="event.maxPrice"> to ${{ event.maxPrice }} </template>
      </span>
    </div>
    <div class="group relative">
      <h3 class="mt-3 text-lg font-semibold leading-6 text-gray-900 group-hover:text-gray-600">
        <a :href="event.eventUrl" target="_blank">
          <span class="absolute inset-0" />
          {{ event.title }}
        </a>
      </h3>
      <p class="mt-5 line-clamp-3 text-sm leading-6 text-gray-600">
        {{ event.description }}
      </p>
    </div>
    <div class="relative mt-8 flex items-center gap-x-4">
      <img
        :src="event.imageUrl"
        alt=""
        class="h-10 w-10 rounded-full bg-gray-50"
      >
      <div class="text-sm leading-6">
        <p class="font-semibold text-gray-900">
          <a :href="event.imageUrl">
            <span class="absolute inset-0" />
            {{ event.theatre }}
          </a>
        </p>
        <p class="text-gray-600">
          {{ event.theatre }}
        </p>
      </div>
    </div>
  </article>
</template>

<script setup>
defineProps({
  event: Object,
})
</script>
