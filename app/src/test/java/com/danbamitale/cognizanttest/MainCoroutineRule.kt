package com.danbamitale.cognizanttest

import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.Executors


@ExperimentalCoroutinesApi
class MainCoroutineRule(
     val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher() //Executors.newSingleThreadExecutor().asCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }
}


@ExperimentalCoroutinesApi
fun MainCoroutineRule.runBlocking(block: suspend () -> Unit): Unit =  kotlinx.coroutines.runBlocking {
    launch (Dispatchers.Main) {
        block()
    }
}


@ExperimentalCoroutinesApi
fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit): Unit = kotlinx.coroutines.test.runBlockingTest {
    launch  {
        block()
    }
}