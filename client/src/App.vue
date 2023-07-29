<template>
  <header class="absolute inset-x-0 top-0 z-50 flex h-16 border-b border-gray-900/10">
    <div class="mx-auto flex w-full max-w-7xl items-center justify-center px-4 sm:px-6 lg:px-8">
      <nav class="hidden md:flex justify-center md:text-sm md:font-semibold md:leading-6 md:text-gray-700">
        <a
          v-for="(item, itemIdx) in navigation"
          :key="itemIdx"
          :href="item.href"
        >{{ item.name }}</a>
      </nav>
    </div>
    <Dialog
      as="div"
      class="lg:hidden"
      :open="mobileMenuOpen"
      @close="mobileMenuOpen = false"
    >
      <div class="fixed inset-0 z-50" />
      <DialogPanel class="fixed inset-y-0 left-0 z-50 w-full overflow-y-auto bg-white px-4 pb-6 sm:max-w-sm sm:px-6 sm:ring-1 sm:ring-gray-900/10">
        <div class="-ml-0.5 flex h-16 items-center gap-x-6">
          <button
            type="button"
            class="-m-2.5 p-2.5 text-gray-700"
            @click="mobileMenuOpen = false"
          >
            <span class="sr-only">Close menu</span>
            <XMarkIcon
              class="h-6 w-6"
              aria-hidden="true"
            />
          </button>
          <div class="-ml-0.5">
            <a
              href="#"
              class="-m-1.5 block p-1.5"
            >
              <span class="sr-only">Your Company</span>
              <img
                class="h-8 w-auto"
                src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
                alt=""
              >
            </a>
          </div>
        </div>
        <div class="mt-2 space-y-2">
          <a
            v-for="item in navigation"
            :key="item.name"
            :href="item.href"
            class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
          >{{ item.name }}</a>
        </div>
      </DialogPanel>
    </Dialog>
  </header>

  <div class="mx-auto max-w-7xl pt-16 lg:flex lg:gap-x-16 lg:px-8">
    <Main />
  </div>
</template>

<script setup>
import Main from './components/Main.vue';

import { ref } from 'vue'
import { Dialog, DialogPanel } from '@headlessui/vue'
import {
  XMarkIcon,
} from '@heroicons/vue/24/outline'

const navigation = [
  { name: 'Theaters', href: '#' },
]

const mobileMenuOpen = ref(false)
</script>
