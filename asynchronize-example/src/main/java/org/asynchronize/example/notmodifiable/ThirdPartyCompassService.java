package org.asynchronize.example.notmodifiable;

public interface ThirdPartyCompassService extends ThirdPartyWestService, ThirdPartyEstService {

  void goToSouth();
}