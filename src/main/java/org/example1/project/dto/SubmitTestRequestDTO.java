package org.example1.project.dto;
import java.util.List;
public class SubmitTestRequestDTO {



        private Long testId;
        private List<SubmitAnswerDTO> answers;

        public SubmitTestRequestDTO() {
        }

        public SubmitTestRequestDTO(Long testId, List<SubmitAnswerDTO> answers) {
            this.testId = testId;
            this.answers = answers;
        }

        public Long getTestId() {
            return testId;
        }

        public void setTestId(Long testId) {
            this.testId = testId;
        }

        public List<SubmitAnswerDTO> getAnswers() {
            return answers;
        }

        public void setAnswers(List<SubmitAnswerDTO> answers) {
            this.answers = answers;
        }


}
