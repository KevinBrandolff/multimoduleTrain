package com.services;

import com.models.Profile;
import com.models.UserNetflix;

public interface ManagementServiceAccounts {

    Profile addNetflixService(UserNetflix userNetflix, Integer profileId );

}
