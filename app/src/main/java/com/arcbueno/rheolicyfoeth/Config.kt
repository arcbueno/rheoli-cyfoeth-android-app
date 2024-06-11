package com.arcbueno.rheolicyfoeth

import android.content.Context
import com.arcbueno.rheolicyfoeth.data.AppDatabase
import com.arcbueno.rheolicyfoeth.data.DepartmentDao
import com.arcbueno.rheolicyfoeth.data.ItemDao
import com.arcbueno.rheolicyfoeth.data.ItemMovingDao
import com.arcbueno.rheolicyfoeth.data.KeyDao
import com.arcbueno.rheolicyfoeth.pages.createdepartment.CreateDepartmentViewModel
import com.arcbueno.rheolicyfoeth.pages.createitem.CreateItemViewModel
import com.arcbueno.rheolicyfoeth.pages.departmentdetail.DepartmentDetailViewModel
import com.arcbueno.rheolicyfoeth.pages.departmentlist.DepartmentListViewModel
import com.arcbueno.rheolicyfoeth.pages.home.HomeViewModel
import com.arcbueno.rheolicyfoeth.pages.itemdetail.ItemDetailViewModel
import com.arcbueno.rheolicyfoeth.pages.itemlist.ItemListViewModel
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemMovingRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import com.arcbueno.rheolicyfoeth.repositories.KeyRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

object Config {
    fun startConfig(context: Context) {
        val appModule = module {

            // DAOs
            single<ItemDao> { AppDatabase.getDatabase(context).itemDao() }
            single<KeyDao> { AppDatabase.getDatabase(context).keyDao() }
            single<DepartmentDao> {
                AppDatabase.getDatabase(context).departmentDao()
            }
            single<ItemMovingDao> {
                AppDatabase.getDatabase(context).itemMovingDao()
            }

            // Repositories
            single { DepartmentRepository(get()) }
            single { ItemRepository(get()) }
            single { KeyRepository(get()) }
            single { ItemMovingRepository(get()) }

            // Viewmodels
            factory { HomeViewModel(get(), get()) }
            factory { CreateItemViewModel(get(), get()) }
            factory { DepartmentListViewModel(get()) }
            factory { ItemListViewModel(get(), get(), get()) }
            factory { ItemDetailViewModel(get(), get(), get()) }
            factory { CreateDepartmentViewModel(get()) }
            factory { DepartmentDetailViewModel(get(), get()) }

        }
        startKoin {
            androidLogger()
            androidContext(context)

            modules(appModule)
        }
    }
}