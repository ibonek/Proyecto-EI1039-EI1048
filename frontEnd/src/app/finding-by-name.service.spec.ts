import { TestBed } from '@angular/core/testing';

import { FindingByNameService } from './finding-by-name.service';

describe('FindingByNameService', () => {
  let service: FindingByNameService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FindingByNameService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
