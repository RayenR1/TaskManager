import { TestBed } from '@angular/core/testing';

import { GetListUsersService } from './get-list-users.service';

describe('GetListUsersService', () => {
  let service: GetListUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetListUsersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
