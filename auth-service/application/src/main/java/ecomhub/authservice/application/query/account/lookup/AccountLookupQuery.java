package ecomhub.authservice.application.query.account.lookup;

import ecomhub.authservice.application.query.abstracts.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountLookupQuery implements IQuery<Boolean> {
    private String identifier;
}
