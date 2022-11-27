package pers.swd.rpc.api;

import pers.swd.rpc.pojo.User;

public interface IUserService {

    User getById(int id);
}
