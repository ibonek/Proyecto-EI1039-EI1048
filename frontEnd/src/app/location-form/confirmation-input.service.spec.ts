import { TestBed } from '@angular/core/testing';

import { ConfirmationInputService } from './confirmation-input.service';

describe('ConfirmationInputService', () => {
  let service: ConfirmationInputService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfirmationInputService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
