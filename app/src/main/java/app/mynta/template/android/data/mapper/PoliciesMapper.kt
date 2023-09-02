package app.mynta.template.android.data.mapper

import app.mynta.template.android.data.source.remote.dto.policies.PoliciesDto
import app.mynta.template.android.domain.model.policies.Policies

fun PoliciesDto.toPolicies(): Policies {
    val policies = this.policies
    return Policies(
        privacy = Policies.Privacy(
            content = policies.privacy.content
        ),
        terms = Policies.Terms(
            content = policies.terms.content
        ),
        response = Policies.Response(
            code = policies.response.code
        )
    )
}