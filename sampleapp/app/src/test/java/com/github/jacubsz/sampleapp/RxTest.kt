package com.github.jacubsz.sampleapp

import org.junit.Rule

abstract class RxTest {

    @get:Rule val schedulers = RxImmediateSchedulerRule()

}