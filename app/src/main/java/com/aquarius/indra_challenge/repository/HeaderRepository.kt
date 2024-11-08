package com.aquarius.indra_challenge.repository

import androidx.lifecycle.LiveData
import com.aquarius.indra_challenge.data.HeaderDao
import com.aquarius.indra_challenge.data.HeaderEntity


class HeaderRepository(private val dao: HeaderDao) {

    val header: LiveData<HeaderEntity> = dao.getHeader()
}