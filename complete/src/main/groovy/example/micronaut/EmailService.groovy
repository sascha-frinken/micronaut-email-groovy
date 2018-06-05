package example.micronaut

import groovy.transform.CompileStatic

@CompileStatic
interface EmailService {
    void send(Email email)
}