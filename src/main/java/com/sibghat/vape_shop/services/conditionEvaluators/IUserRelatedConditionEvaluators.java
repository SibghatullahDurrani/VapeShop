package com.sibghat.vape_shop.services.conditionEvaluators;

import com.sibghat.vape_shop.dtos.user.AddUserDto;

public interface IUserRelatedConditionEvaluators {
    void checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(AddUserDto userToAdd);

    void checkThatContactNumberDoesNotAlreadyExistsBeforeUpdatingUser(String contactNumber);
}
